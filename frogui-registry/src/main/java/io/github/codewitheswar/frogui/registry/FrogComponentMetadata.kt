package io.github.codewitheswar.frogui.registry

import androidx.compose.runtime.Immutable

/**
 * Metadata defining a documented property/parameter on a FrogUI component.
 */
@Immutable
data class ComponentPropertyMetadata(
    val name: String,
    val type: String,
    val defaultValue: String,
    val description: String
)

/**
 * Metadata defining a live example for a FrogUI component.
 */
@Immutable
data class ComponentExampleMetadata(
    val id: String,
    val title: String,
    val description: String,
    val codeSnippet: String
)

/**
 * Native projection of canonical registry JSON. Contains data, never UI factories.
 */
@Immutable
data class FrogComponentMetadata(
    val id: String,
    val name: String,
    val description: String,
    val category: FrogComponentCategory,
    val status: FrogComponentStatus,
    val since: String,
    val docsPath: String,
    val properties: List<ComponentPropertyMetadata> = emptyList(),
    val examples: List<ComponentExampleMetadata> = emptyList(),
    val displayName: String = name,
    val variants: List<String> = emptyList(),
    val sizes: List<String> = emptyList(),
    val showcaseRoute: String = docsPath
)
