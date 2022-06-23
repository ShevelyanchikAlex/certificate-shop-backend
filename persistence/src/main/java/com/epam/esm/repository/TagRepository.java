package com.epam.esm.repository;

import com.epam.esm.domain.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    String FIND_MOST_POPULAR_TAGS_QUERY = """
            SELECT tag.id, tag.name FROM tag
            JOIN gift_certificate_has_tag gcht ON tag.id = gcht.tag_id AND gcht.gift_certificate_id IN
            (SELECT gift_certificate.id FROM gift_certificate
            JOIN order_has_gift_certificate ohgc ON gift_certificate.id = ohgc.gift_certificate_id
            JOIN orders ON gift_certificate.id = ohgc.gift_certificate_id AND orders.user_id = (
            SELECT user.id FROM user
            JOIN orders ON user.id = orders.user_id
            JOIN gift_certificate gc ON gc.id = ohgc.gift_certificate_id
            GROUP BY user.id
            ORDER BY SUM(orders.total_price) DESC LIMIT 1))
            GROUP BY gcht.tag_id
            ORDER BY COUNT(gcht.gift_certificate_id) DESC""";

    /**
     * Finds Tag with name
     *
     * @param name Name of Tag
     * @return Founded Tag
     */
    Tag getTagByName(String name);

    /**
     * Finds most popular Tags which are included in Certificates included in Orders
     *
     * @param pageable Pageable
     * @return List of Tags
     */
    @Query(value = FIND_MOST_POPULAR_TAGS_QUERY, nativeQuery = true)
    List<Tag> findMostPopularTags(Pageable pageable);

    /**
     * Checks if there is a Tag with name
     *
     * @param name Name of Tag
     * @return true if exists, otherwise false
     */
    boolean existsTagByName(String name);
}
