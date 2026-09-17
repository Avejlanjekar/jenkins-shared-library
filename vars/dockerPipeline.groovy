def call(String imagename, String credentialsId, String registryUrl){
    echo "Building docker image ${imagename}"
    def app= docker.build("${imagename}:${GIT_COMMIT}")

    docker.withRegistry(
        "${registryUrl}",
        "${credentialsId}"
    )
    {
        app.push()
    }
}