package pt.umaia.tempofacil.data.mappers

interface ApiMapper<Domain, Entity> {
    fun mapToDomain(apiEntity: Entity): Domain
}