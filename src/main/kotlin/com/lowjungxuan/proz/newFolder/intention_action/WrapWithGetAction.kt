package com.lowjungxuan.proz.newFolder.intention_action

class WrapWithGetBuilderAction : WrapWithAction(SnippetType.GetBuilder) {
    override fun getText(): String {
        return "Wrap with GetBuilder"
    }
}

class WrapWithGetBuilderAutoDisposeAction : WrapWithAction(SnippetType.GetBuilderAutoDispose) {
    override fun getText(): String {
        return "Wrap with GetBuilder (Auto Dispose)"
    }
}

class WrapWithGetXAction : WrapWithAction(SnippetType.GetX) {
    override fun getText(): String {
        return "Wrap with GetX"
    }
}

class WrapWithObxAction : WrapWithAction(SnippetType.Obx) {
    override fun getText(): String {
        return "Wrap with Obx"
    }
}

class WrapWithBlocBuilderAction : WrapWithAction(SnippetType.BlocBuilder) {
    override fun getText(): String {
        return "Wrap with BlocBuilder"
    }
}

class WrapWithBlocConsumerAction : WrapWithAction(SnippetType.BlocConsumer) {
    override fun getText(): String {
        return "Wrap with BlocConsumer"
    }
}

class WrapWithBlocListenerAction : WrapWithAction(SnippetType.BlocListener) {
    override fun getText(): String {
        return "Wrap with BlocListener"
    }
}

class WrapWithBlocProviderAction : WrapWithAction(SnippetType.BlocProvider) {
    override fun getText(): String {
        return "Wrap with BlocProvider"
    }
}

class WrapWithRepositoryProviderAction : WrapWithAction(SnippetType.RepositoryProvider) {
    override fun getText(): String {
        return "Wrap with RepositoryProvider"
    }
}
