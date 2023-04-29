package om.zrcoding.skincare.data.repositories

import com.zrcoding.skincare.data.domain.model.Volume
import com.zrcoding.skincare.data.domain.model.VolumeUnit
import com.zrcoding.skincare.data.domain.repositories.VolumeRepository
import kotlinx.coroutines.flow.flowOf
import java.util.UUID
import javax.inject.Inject

class VolumeRepositoryMockSuccess @Inject constructor() : VolumeRepository {
    override fun getAll() = flowOf(fakeVolumes)

    override suspend fun getOne(uuid: String) = fakeVolumes.find { it.uuid == uuid }
}

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