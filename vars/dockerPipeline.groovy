def call(){
    pipeline{
        agent any

        environment{
            Docker_image= 'avejlanjekar45/jenkins-docker-pipeline-using-sl'
            Registry_url= 'https://registry.hub.docker.com'
            Credentials='dockerhub-credentials'
        }

        stages{
            stage("checkout"){
                steps{
                    checkout scm
                }
            }

            stage("docker build & Push"){
                steps{
                    script{
                        def app=docker.build("${Docker_image}:${GIT_COMMIT}")

                        docker.withRegistry(
                            "${Registry_url}",
                            "${Credentials}"
                        )

                        {
                            app.push()
                        }
                    }
                    
                }

            }
        }
    }
}