package io.github.codewitheswar.frogui.registry

/** Data-only catalog generated from canonical component JSON at build time. */
object FrogComponentRegistry {
    val allComponents: List<FrogComponentMetadata> = generatedComponents

    /** Architectural reference; remains Experimental until its stability review is complete. */
    val Button: FrogComponentMetadata = allComponents.single { it.id == "button" }

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
