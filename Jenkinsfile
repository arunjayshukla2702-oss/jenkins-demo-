pipeline {
    agent any

    environment {
        DH_USER = 'arunjayshukla'
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
                sh 'ls -la'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn -B clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn -B test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                sh 'mvn -B package -DskipTests'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t demo-app:${BUILD_NUMBER} .'
            }
        }

        stage('Docker Push') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'dockerhub-creds',
                    usernameVariable: 'DH_USER_CRED',
                    passwordVariable: 'DH_PASS'
                )]) {
                    sh '''
                        echo "$DH_PASS" | docker login -u "$DH_USER_CRED" --password-stdin
                        docker tag demo-app:${BUILD_NUMBER} $DH_USER_CRED/demo-app:${BUILD_NUMBER}
                        docker push $DH_USER_CRED/demo-app:${BUILD_NUMBER}
                    '''
                }
            }
            post {
                always {
                    sh 'docker logout || true'
                }
            }
        }

        stage('Deploy Local') {
    when {
        expression { env.GIT_BRANCH == 'origin/main' || env.BRANCH_NAME == 'main' }
    }
    steps {
        sh '''
            docker stop demo || true
            docker rm -f demo || true
            sleep 3
            docker run -d --name demo -p 8081:8080 \
              --restart unless-stopped \
              demo-app:${BUILD_NUMBER}
            sleep 5
            curl -f http://localhost:8081/health
        '''
    }
}
    }

    post {
        success { echo 'Pipeline PASS' }
        failure { echo 'Pipeline FAIL' }
    }
}
