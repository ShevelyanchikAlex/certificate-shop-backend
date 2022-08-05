    pipeline {
        agent any

        stages {
            stage('Build') {
                steps {
                    git 'https://github.com/ShevelyanchikAlex/mjs-rest-api-basics.git'
                    sh './gradlew clean compileJava'
                }
            }
            stage('Test') {
                steps {
                    sh './gradlew test'
                }
                post {
                    always {
                        junit 'service/src/test/resources/testngSampleTest.xml'
                    }
                }
            }
            stage('SonarQube Analysis') {
                steps {
                    withSonarQubeEnv(installationName:'sq1') {
                        sh "./gradlew sonarqube"
                    }
                    jacoco(
                            execPattern:'service/src/test/resources/*.exec',
                            classPattern:'service/src/test/resources/classes',
                            sourcePattern:'web/src/main/java/com/epam/esm/config',
                            exclusionPattern:'service/src/test*'
                    )
                }
            }
        }
    }