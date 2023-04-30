package om.zrcoding.skincare.data.repositories

import com.zrcoding.skincare.data.domain.model.Category
import com.zrcoding.skincare.data.domain.repositories.CategoryRepository
import kotlinx.coroutines.flow.flowOf
import java.util.UUID
import javax.inject.Inject

class CategoryRepositoryMockSuccess @Inject constructor() : CategoryRepository {

    override fun getAll() = flowOf(fakeCategories)

    override suspend fun getOne(uuid: String) = fakeCategories.find { it.uuid == uuid }
}

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