pipeline {
    agent any

    environment {
        DOCKER_CREDENTIALS = credentials('docker-hub-credentials')
        JAVA_HOME = tool 'JDK17'
        PATH = "${JAVA_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Validate Files') {
            steps {
                script {
                    if (!fileExists('pom.xml')) {
                        error "Error: pom.xml not found!"
                    }
                    if (!fileExists('Dockerfile')) {
                        error "Error: Dockerfile not found!"
                    }
                }
            }
        }

        stage('Install Docker Compose') {
            steps {
                sh '''
                    sudo curl -L "https://github.com/docker/compose/releases/download/v2.20.2/docker-compose-$(uname -s)-$(uname -m)" \
                        -o /usr/local/bin/docker-compose
                    sudo chmod +x /usr/local/bin/docker-compose
                    docker-compose version
                '''
            }
        }

        stage('Build and Test') {
            steps {
                cache(path: '~/.m2/repository', key: "${env.JOB_NAME}") {
                    sh 'mvn clean verify'
                }
            }
            post {
                failure {
                    error "Build or tests failed!"
                }
            }
        }

        stage('Check Coverage') {
            steps {
                script {
                    def coverage = sh(
                        script: "mvn jacoco:report | grep -o 'Total.*' | awk '{print \$4}' | cut -d'%' -f1",
                        returnStdout: true
                    ).trim()

                    if (coverage.toInteger() < 80) {
                        echo "Warning: Test coverage is below 80%: ${coverage}%"
                    }
                }
            }
        }

        stage('Docker Build and Push') {
            steps {
                script {
                    def buildDate = sh(script: 'date -u +"%Y-%m-%d_%H:%M:%S"', returnStdout: true).trim()
                    def gitCommit = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()

                    withCredentials([usernamePassword(
                        credentialsId: 'docker-hub-credentials',
                        usernameVariable: 'DOCKER_USERNAME',
                        passwordVariable: 'DOCKER_PASSWORD'
                    )]) {
                        sh """
                            echo ${DOCKER_PASSWORD} | docker login -u ${DOCKER_USERNAME} --password-stdin

                            docker build \
                                --build-arg BUILD_DATE="${buildDate}" \
                                --build-arg GIT_COMMIT="${gitCommit}" \
                                -t ${DOCKER_USERNAME}/mini-shop-management-system:latest \
                                -t ${DOCKER_USERNAME}/mini-shop-management-system:${BUILD_NUMBER} .

                            docker push ${DOCKER_USERNAME}/mini-shop-management-system:latest
                            docker push ${DOCKER_USERNAME}/mini-shop-management-system:${BUILD_NUMBER}
                        """
                    }
                }
            }
        }

        stage('Verify Docker Image') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker-hub-credentials',
                    usernameVariable: 'DOCKER_USERNAME',
                    passwordVariable: 'DOCKER_PASSWORD'
                )]) {
                    sh """
                        docker pull ${DOCKER_USERNAME}/mini-shop-management-system:latest
                        docker inspect ${DOCKER_USERNAME}/mini-shop-management-system:latest
                    """
                }
            }
        }

        stage('Create Release Tag') {
            when {
                branch 'main'
            }
            steps {
                script {
                    def currentDate = sh(script: 'date -u +"%Y.%m.%d"', returnStdout: true).trim()
                    def tagName = "v${currentDate}-${BUILD_NUMBER}"

                    sh """
                        git tag ${tagName}
                        git push origin ${tagName}
                        echo "Created and pushed tag: ${tagName}"
                    """
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}