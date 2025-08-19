import type { INode, INodeParams } from '../../Interface'

class RedisEmbeddingsCache implements INode {
    label: string
    name: string
    version: number
    description: string
    type: string
    icon: string
    category: string
    baseClasses: string[]
    inputs: INodeParams[]
    credential: INodeParams

    constructor() {
        this.label = 'Redis Embeddings Cache'
        this.name = 'redisEmbeddingsCache'
        this.version = 1.0
        this.type = 'RedisEmbeddingsCache'
        this.description = 'Cache generated Embeddings in Redis to avoid needing to recompute them.'
        this.icon = 'cache/RedisCache/redis.svg'
        this.category = 'Cache'
        this.baseClasses = [this.type]
        this.credential = {
            label: 'Connect Credential',
            name: 'credential',
            type: 'credential',
            optional: true,
            credentialNames: ['redisCacheApi', 'redisCacheUrlApi']
        }
        this.inputs = [
            {
                label: 'Embeddings',
                name: 'embeddings',
                type: 'Embeddings'
            },
            {
                label: 'Time to Live (ms)',
                name: 'ttl',
                type: 'number',
                step: 10,
                default: 60 * 60,
                optional: true,
                additionalParams: true
            },
            {
                label: 'Namespace',
                name: 'namespace',
                type: 'string',
                optional: true,
                additionalParams: true
            }
        ]
    }
}

export default   RedisEmbeddingsCache
