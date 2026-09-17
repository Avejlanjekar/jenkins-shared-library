def call(String imagename){
    echo "Building docker image ${imagename}"
    def app= docker.build("${imagename}:${GIT_COMMIT}")

    docker.withRegistry(
        'https://registry.hub.docker.com',
        'dockerhub-credentials'
    )
    {
        app.push()
    }
}