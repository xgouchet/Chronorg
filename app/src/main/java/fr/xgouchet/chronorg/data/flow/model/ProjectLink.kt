package fr.xgouchet.chronorg.data.flow.model

data class ProjectLink(
    val project: Project,
    val link: Type
) {

    enum class Type {
        ENTITIES,
        PORTALS,
        EVENTS
    }
}