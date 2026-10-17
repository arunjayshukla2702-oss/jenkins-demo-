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
                echo 'Building...'
                sh 'cat README.md'
            }
        }
        stage('Test') {
            steps {
                echo 'Running tests...'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying...'
            }
        }
    }

    post {
        success { echo 'Pipeline PASS' }
        failure { echo 'Pipeline FAIL' }
    }
}
