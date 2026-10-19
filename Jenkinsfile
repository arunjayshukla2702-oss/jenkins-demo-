pipeline {
    agent any

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
                    credentialsId: 'dockerhub-cred',
                    usernameVariable: 'DH_USER',
                    passwordVariable: 'DH_PASS'
                )]) {
                    sh '''
                        echo "$DH_PASS" | docker login -u "$DH_USER" --password-stdin
                        docker tag demo-app:${BUILD_NUMBER} $DH_USER/demo-app:${BUILD_NUMBER}
                        docker push $DH_USER/demo-app:${BUILD_NUMBER}
                    '''
                }
            }
            post {
                always { sh 'docker logout' }
            }
        }
        stage('Docker Run') {
            steps {
                sh 'docker run --rm demo-app:${BUILD_NUMBER}'
            }
        }
    }

    post {
        success { echo 'Pipeline PASS' }
        failure { echo 'Pipeline FAIL' }
    }
}
