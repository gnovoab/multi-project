
allprojects{
    task("hello").doLast{
        println("I'm ${project.name}")
    }
}