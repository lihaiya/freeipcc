import type { INode, INodeParams } from '../../Interface'

class ChatPromptTemplate_Prompts implements INode {
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
        this.label = 'Chat Prompt Template'
        this.name = 'chatPromptTemplate'
        this.version = 1.0
        this.type = 'ChatPromptTemplate'
        this.icon = 'prompts/ChatPromptTemplate/prompt.svg'
        this.category = 'Prompts'
        this.description = 'Schema to represent a chat prompt'
        this.baseClasses = [this.type]
        this.inputs = [
            {
                label: 'System Message',
                name: 'systemMessagePrompt',
                type: 'string',
                rows: 4,
                placeholder: `You are a helpful assistant that translates {input_language} to {output_language}.`
            },
            {
                label: 'Human Message',
                name: 'humanMessagePrompt',
                type: 'string',
                rows: 4,
                placeholder: `{text}`
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

export default ChatPromptTemplate_Prompts
