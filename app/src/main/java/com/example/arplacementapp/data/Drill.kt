package com.example.arplacementapp.data

data class Drill(
    val id: String,
    val name: String,
    val description: String,
    val tips: String,
    val imageUrl: String
)

val mockDrills = listOf(
    Drill(
        id = "drill_1",
        name = "Impact Drill Pro",
        description = "A powerful impact drill suitable for heavy-duty tasks. Features a brushless motor for extended life and efficiency.",
        tips = "Always wear safety goggles. Ensure the bit is securely fastened. Use appropriate drill bits for the material.",
        imageUrl = "https://placehold.co/600x400/FF5733/FFFFFF?text=Drill+1"
    ),
    Drill(
        id = "drill_2",
        name = "Cordless Screwdriver",
        description = "Lightweight and compact cordless screwdriver, perfect for household repairs and assembly. Comes with a magnetic bit holder.",
        tips = "Charge fully before first use. Do not overtighten screws. Keep away from water.",
        imageUrl = "https://placehold.co/600x400/33FF57/FFFFFF?text=Drill+2"
    ),
    Drill(
        id = "drill_3",
        name = "Hammer Drill Max",
        description = "Designed for drilling into masonry and concrete. Offers both rotary and hammer drilling modes. Ergonomic design for comfortable use.",
        tips = "Use carbide-tipped bits for masonry. Apply steady pressure. Clean dust regularly from the work area.",
        imageUrl = "https://placehold.co/600x400/3357FF/FFFFFF?text=Drill+3"
    )
)

val mockDrillsMap: Map<String, Drill> = mockDrills.associateBy { it.id }

