package io.github.codewitheswar.frogui.registry

/** Data-only catalog generated from canonical component JSON at build time. */
object FrogComponentRegistry {
    val allComponents: List<FrogComponentMetadata> = generatedComponents

    /** Architectural reference; remains Experimental until its stability review is complete. */
    val Button: FrogComponentMetadata = allComponents.single { it.id == "button" }

    /** Compact action control; Experimental. */
    val IconButton: FrogComponentMetadata = allComponents.single { it.id == "icon-button" }

    /** Prominent floating action control; Experimental. */
    val Fab: FrogComponentMetadata = allComponents.single { it.id == "fab" }

    /** State-hoisted form input; Experimental. */
    val TextField: FrogComponentMetadata = allComponents.single { it.id == "text-field" }

    fun findById(id: String): FrogComponentMetadata? =
        allComponents.firstOrNull { it.id.equals(id, ignoreCase = true) }

    fun search(query: String, category: FrogComponentCategory? = null): List<FrogComponentMetadata> {
        val trimmed = query.trim().lowercase()
        return allComponents.filter { item ->
            val matchesCategory = category == null || item.category == category
            val matchesQuery = trimmed.isEmpty() ||
                item.name.lowercase().contains(trimmed) ||
                item.description.lowercase().contains(trimmed) ||
                item.category.displayName.lowercase().contains(trimmed)
            matchesCategory && matchesQuery
        }
    }
}
