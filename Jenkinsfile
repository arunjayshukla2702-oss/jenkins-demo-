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
        }stage('Package') {
    steps { sh 'mvn -B package -DskipTests' }
    post {
        success { archiveArtifacts artifacts: 'target/*.jar', fingerprint: true }
    }
}
stage('Docker Build') {
    steps { sh 'docker build -t demo-app:${BUILD_NUMBER} .' }
}
stage('Docker Run') {
    steps { sh 'docker run --rm demo-app:${BUILD_NUMBER}' }
}
        stage('Run') {
            steps {
                sh 'java -jar target/demo-app-1.0.jar'
            }
        }
    }

    post {
        success { echo 'Pipeline PASS' }
        failure { echo 'Pipeline FAIL' }
    }
}
