import type { INode, INodeParams } from '../../Interface'
import {getBaseClasses} from "@/pages/gptchat/nodes/nodesUtils";
import { RedisCache as LangchainRedisCache } from 'langchain/cache/ioredis'

class RedisCache implements INode {
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
        this.label = 'Redis Cache'
        this.name = 'redisCache'
        this.version = 1.0
        this.type = 'RedisCache'
        this.description = 'Cache LLM response in Redis, useful for sharing cache across multiple processes or servers'
        this.icon = 'cache/RedisCache/redis.svg'
        this.category = 'Cache'
        this.baseClasses = [this.type, ...getBaseClasses(LangchainRedisCache)]
        this.credential = {
            label: 'Connect Credential',
            name: 'credential',
            type: 'credential',
            optional: true,
            credentialNames: ['redisCacheApi', 'redisCacheUrlApi']
        }
        this.inputs = [
            {
                label: 'Time to Live (ms)',
                name: 'ttl',
                type: 'number',
                step: 1,
                optional: true,
                additionalParams: true
            }
        ]
    }
}

export default  RedisCache
