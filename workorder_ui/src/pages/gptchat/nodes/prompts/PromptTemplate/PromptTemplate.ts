import type { INode, INodeParams } from '../../Interface'

class PromptTemplate_Prompts implements INode {
    label: string
    name: string
    version: number
    description: string
    type: string
    icon: string
    category: string
    baseClasses: string[]
    inputs: INodeParams[]

    constructor() {
        this.label = 'Prompt Template'
        this.name = 'promptTemplate'
        this.version = 1.0
        this.type = 'PromptTemplate'
        this.icon = 'prompts/PromptTemplate/prompt.svg'
        this.category = 'Prompts'
        this.description = 'Schema to represent a basic prompt for an LLM'
        this.baseClasses = [this.type]
        this.inputs = [
            {
                label: 'Template',
                name: 'template',
                type: 'string',
                rows: 4,
                placeholder: `What is a good name for a company that makes {product}?`
            },
            {
                label: 'Format Prompt Values',
                name: 'promptValues',
                type: 'json',
                optional: true,
                acceptVariable: true,
                list: true
            }
        ]
    }
}
export default PromptTemplate_Prompts
