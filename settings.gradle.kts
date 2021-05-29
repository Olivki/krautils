include("codegen", "core", "extensions:scalr", "extensions:compress")

rootProject.name = "krautils"

project(":core").name = "krautils-core"
project(":extensions:scalr").name = "krautils-scalr"
project(":extensions:compress").name = "krautils-compress"