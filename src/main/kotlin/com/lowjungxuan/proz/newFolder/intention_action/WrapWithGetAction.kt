package com.lowjungxuan.proz.newFolder.intention_action

class WrapWithGetBuilderAction : WrapWithGetAction(SnippetType.GetBuilder) {
    override fun getText(): String {
        return "Wrap with GetBuilder"
    }
}

class WrapWithGetBuilderAutoDisposeAction : WrapWithGetAction(SnippetType.GetBuilderAutoDispose) {
    override fun getText(): String {
        return "Wrap with GetBuilder (Auto Dispose)"
    }
}

class WrapWithGetXAction : WrapWithGetAction(SnippetType.GetX) {
    override fun getText(): String {
        return "Wrap with GetX"
    }
}

class WrapWithObxAction : WrapWithGetAction(SnippetType.Obx) {
    override fun getText(): String {
        return "Wrap with Obx"
    }
}

class WrapWithBlocBuilderAction : WrapWithBlocAction(SnippetType.BlocBuilder) {
    override fun getText(): String {
        return "Wrap with BlocBuilder"
    }
}

class WrapWithBlocConsumerAction : WrapWithBlocAction(SnippetType.BlocConsumer) {
    override fun getText(): String {
        return "Wrap with BlocConsumer"
    }
}

class WrapWithBlocListenerAction : WrapWithBlocAction(SnippetType.BlocListener) {
    override fun getText(): String {
        return "Wrap with BlocListener"
    }
}

class WrapWithBlocProviderAction : WrapWithBlocAction(SnippetType.BlocProvider) {
    override fun getText(): String {
        return "Wrap with BlocProvider"
    }
}

class WrapWithRepositoryProviderAction : WrapWithBlocAction(SnippetType.RepositoryProvider) {
    override fun getText(): String {
        return "Wrap with RepositoryProvider"
    }
}
