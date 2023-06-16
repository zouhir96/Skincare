package com.zrcoding.skincare.data.sources.fake

import com.zrcoding.skincare.data.domain.model.Category
import com.zrcoding.skincare.data.domain.model.Product
import com.zrcoding.skincare.data.domain.model.Volume
import com.zrcoding.skincare.data.domain.model.VolumeUnit
import java.util.UUID

val fakeCategories = listOf(
    Category(
        uuid = UUID.randomUUID().toString(),
        name = "Handbody",
    ),
    Category(
        uuid = UUID.randomUUID().toString(),
        name = "Serum",
    ),
    Category(
        uuid = UUID.randomUUID().toString(),
        name = "Toner",
    ),
    Category(
        uuid = UUID.randomUUID().toString(),
        name = "Cleanser",
    ),
)

val fakeVolumes = listOf(
    Volume(
        UUID.randomUUID().toString(),
        50,
        VolumeUnit.ML
    ),
    Volume(
        UUID.randomUUID().toString(),
        100,
        VolumeUnit.ML
    ),
    Volume(
        UUID.randomUUID().toString(),
        150,
        VolumeUnit.ML
    )
)

val fakeProducts = listOf(
    Product(
        uuid = UUID.randomUUID().toString(),
        name = "Circumference Active Botanical Refining Toner",
        title = "Circumference Active Botanical Refining Toner",
        description = "Protect the skin you're in with this reef-safe, non-nano, coated zinc oxide-based sunscreen that offers sheer, long-lasting, and broad-spectrum coverage. Lightweight UVA/UVB mineral protection. Oleosome protected zinc... Read more",
        price = 60.0,
        stock = 100,
        stars = 5,
        reviews = 100,
        imagesUrls = listOf(
            "https://8c3412d76225d04d7baa-be98b6ea17920953fb931282eff9a681.images.lovelyskin.com/jj32dkws_202206201747084860.jpg",
            "https://static.thcdn.com/images/large/original/productimg/1600/1600/11340437-2054432772871696.jpg",
            "https://cdn.shopify.com/s/files/1/0250/0935/6909/products/SmoothingFace_NeckCream_Skincare_30mL_Version1_700px_600x.jpg?v=1622148059"
        ),
        volumes = fakeVolumes,
        category = fakeCategories[0]
    ),
    Product(
        uuid = UUID.randomUUID().toString(),
        name = "Protect Available Exclusively from a Skincare",
        title = "Protect Available Exclusively from a Skincare",
        description = "Protect the skin you're in with this reef-safe, non-nano, coated zinc oxide-based sunscreen that offers sheer, long-lasting, and broad-spectrum coverage. Lightweight UVA/UVB mineral protection. Oleosome protected zinc... Read more",
        price = 100.0,
        stock = 102,
        stars = 5,
        reviews = 200,
        imagesUrls = listOf(
            "https://8c3412d76225d04d7baa-be98b6ea17920953fb931282eff9a681.images.lovelyskin.com/jj32dkws_202206201747084860.jpg",
            "https://static.thcdn.com/images/large/original/productimg/1600/1600/11340437-2054432772871696.jpg",
            "https://cdn.shopify.com/s/files/1/0250/0935/6909/products/SmoothingFace_NeckCream_Skincare_30mL_Version1_700px_600x.jpg?v=1622148059"
        ),
        volumes = fakeVolumes,
        category = fakeCategories[1]
    ),
    Product(
        uuid = UUID.randomUUID().toString(),
        name = "Environ package skin EssentiA*",
        title = "Environ package skin EssentiA*",
        description = "Protect the skin you're in with this reef-safe, non-nano, coated zinc oxide-based sunscreen that offers sheer, long-lasting, and broad-spectrum coverage. Lightweight UVA/UVB mineral protection. Oleosome protected zinc... Read more",
        price = 190.0,
        stock = 108,
        stars = 5,
        reviews = 3400,
        imagesUrls = listOf(
            "https://8c3412d76225d04d7baa-be98b6ea17920953fb931282eff9a681.images.lovelyskin.com/jj32dkws_202206201747084860.jpg",
            "https://static.thcdn.com/images/large/original/productimg/1600/1600/11340437-2054432772871696.jpg",
            "https://cdn.shopify.com/s/files/1/0250/0935/6909/products/SmoothingFace_NeckCream_Skincare_30mL_Version1_700px_600x.jpg?v=1622148059"
        ),
        volumes = fakeVolumes,
        category = fakeCategories[2]
    ),
    Product(
        uuid = UUID.randomUUID().toString(),
        name = "Circumference Active Botanical Refining Toner",
        title = "Circumference Active Botanical Refining Toner",
        description = "Protect the skin you're in with this reef-safe, non-nano, coated zinc oxide-based sunscreen that offers sheer, long-lasting, and broad-spectrum coverage. Lightweight UVA/UVB mineral protection. Oleosome protected zinc... Read more",
        price = 60.0,
        stock = 10,
        stars = 5,
        reviews = 2600,
        imagesUrls = listOf(
            "https://8c3412d76225d04d7baa-be98b6ea17920953fb931282eff9a681.images.lovelyskin.com/jj32dkws_202206201747084860.jpg",
            "https://static.thcdn.com/images/large/original/productimg/1600/1600/11340437-2054432772871696.jpg",
            "https://cdn.shopify.com/s/files/1/0250/0935/6909/products/SmoothingFace_NeckCream_Skincare_30mL_Version1_700px_600x.jpg?v=1622148059"
        ),
        volumes = fakeVolumes,
        category = fakeCategories[3]
    ),
    Product(
        uuid = UUID.randomUUID().toString(),
        name = "Protect Available Exclusively from a Skincare",
        title = "Protect Available Exclusively from a Skincare",
        description = "Protect the skin you're in with this reef-safe, non-nano, coated zinc oxide-based sunscreen that offers sheer, long-lasting, and broad-spectrum coverage. Lightweight UVA/UVB mineral protection. Oleosome protected zinc... Read more",
        price = 100.0,
        stock = 299,
        stars = 5,
        reviews = 39900,
        imagesUrls = listOf(
            "https://8c3412d76225d04d7baa-be98b6ea17920953fb931282eff9a681.images.lovelyskin.com/jj32dkws_202206201747084860.jpg",
            "https://static.thcdn.com/images/large/original/productimg/1600/1600/11340437-2054432772871696.jpg",
            "https://cdn.shopify.com/s/files/1/0250/0935/6909/products/SmoothingFace_NeckCream_Skincare_30mL_Version1_700px_600x.jpg?v=1622148059"
        ),
        volumes = fakeVolumes,
        category = fakeCategories[0]
    ),
    Product(
        uuid = UUID.randomUUID().toString(),
        name = "Environ package skin EssentiA*",
        title = "Environ package skin EssentiA*",
        description = "Protect the skin you're in with this reef-safe, non-nano, coated zinc oxide-based sunscreen that offers sheer, long-lasting, and broad-spectrum coverage. Lightweight UVA/UVB mineral protection. Oleosome protected zinc... Read more",
        price = 190.0,
        stock = 177,
        stars = 5,
        reviews = 87700,
        imagesUrls = listOf(
            "https://8c3412d76225d04d7baa-be98b6ea17920953fb931282eff9a681.images.lovelyskin.com/jj32dkws_202206201747084860.jpg",
            "https://static.thcdn.com/images/large/original/productimg/1600/1600/11340437-2054432772871696.jpg",
            "https://cdn.shopify.com/s/files/1/0250/0935/6909/products/SmoothingFace_NeckCream_Skincare_30mL_Version1_700px_600x.jpg?v=1622148059"
        ),
        volumes = fakeVolumes,
        category = fakeCategories[1]
    ),
    Product(
        uuid = UUID.randomUUID().toString(),
        name = "Circumference Active Botanical Refining Toner",
        title = "Circumference Active Botanical Refining Toner",
        description = "Protect the skin you're in with this reef-safe, non-nano, coated zinc oxide-based sunscreen that offers sheer, long-lasting, and broad-spectrum coverage. Lightweight UVA/UVB mineral protection. Oleosome protected zinc... Read more",
        price = 60.0,
        stock = 101,
        stars = 5,
        reviews = 877600,
        imagesUrls = listOf(
            "https://8c3412d76225d04d7baa-be98b6ea17920953fb931282eff9a681.images.lovelyskin.com/jj32dkws_202206201747084860.jpg",
            "https://static.thcdn.com/images/large/original/productimg/1600/1600/11340437-2054432772871696.jpg",
            "https://cdn.shopify.com/s/files/1/0250/0935/6909/products/SmoothingFace_NeckCream_Skincare_30mL_Version1_700px_600x.jpg?v=1622148059"
        ),
        volumes = fakeVolumes,
        category = fakeCategories[2]
    ),
    Product(
        uuid = UUID.randomUUID().toString(),
        name = "Protect Available Exclusively from a Skincare",
        title = "Protect Available Exclusively from a Skincare",
        description = "Protect the skin you're in with this reef-safe, non-nano, coated zinc oxide-based sunscreen that offers sheer, long-lasting, and broad-spectrum coverage. Lightweight UVA/UVB mineral protection. Oleosome protected zinc... Read more",
        price = 100.0,
        stock = 200,
        stars = 5,
        reviews = 76600,
        imagesUrls = listOf(
            "https://8c3412d76225d04d7baa-be98b6ea17920953fb931282eff9a681.images.lovelyskin.com/jj32dkws_202206201747084860.jpg",
            "https://static.thcdn.com/images/large/original/productimg/1600/1600/11340437-2054432772871696.jpg",
            "https://cdn.shopify.com/s/files/1/0250/0935/6909/products/SmoothingFace_NeckCream_Skincare_30mL_Version1_700px_600x.jpg?v=1622148059"
        ),
        volumes = fakeVolumes,
        category = fakeCategories[3]
    ),
    Product(
        uuid = UUID.randomUUID().toString(),
        name = "Environ package skin EssentiA*",
        title = "Environ package skin EssentiA*",
        description = "Protect the skin you're in with this reef-safe, non-nano, coated zinc oxide-based sunscreen that offers sheer, long-lasting, and broad-spectrum coverage. Lightweight UVA/UVB mineral protection. Oleosome protected zinc... Read more",
        price = 190.0,
        stock = 204,
        stars = 5,
        reviews = 76600,
        imagesUrls = listOf(
            "https://8c3412d76225d04d7baa-be98b6ea17920953fb931282eff9a681.images.lovelyskin.com/jj32dkws_202206201747084860.jpg",
            "https://static.thcdn.com/images/large/original/productimg/1600/1600/11340437-2054432772871696.jpg",
            "https://cdn.shopify.com/s/files/1/0250/0935/6909/products/SmoothingFace_NeckCream_Skincare_30mL_Version1_700px_600x.jpg?v=1622148059"
        ),
        volumes = fakeVolumes,
        category = fakeCategories[0]
    )
)