package com.lowjungxuan.proz.newFolder.intention_action

object Snippets {
    fun getSnippet(snippetType: SnippetType?, widget: String): String {
        return when (snippetType) {
            SnippetType.Obx -> snippetObx(widget)
            SnippetType.GetBuilder -> snippetGetBuilder(widget)
            SnippetType.GetBuilderAutoDispose -> snippetGetBuilderAutoDispose(widget)
            SnippetType.GetX -> snippetGetX(widget)
            SnippetType.BlocBuilder -> blocBuilderSnippet(widget)
            SnippetType.BlocListener -> blocListenerSnippet(widget)
            SnippetType.BlocProvider -> blocProviderSnippet(widget)
            SnippetType.BlocConsumer -> blocConsumerSnippet(widget)
            SnippetType.RepositoryProvider -> repositoryProviderSnippet(widget)
            else -> ""
        }
    }

    private fun snippetObx(widget: String): String {
        return String.format(
            """Obx(() {
  return %1${"$"}s;
})""", widget
        )
    }

    private fun snippetGetBuilder(widget: String): String {
        return String.format(
            """GetBuilder<%1${"$"}s>(builder: (%2${"$"}s) {
  return %3${"$"}s;
})""", "SubjectController", "controller", widget
        )
    }

    private fun snippetGetBuilderAutoDispose(widget: String): String {
        return String.format(
            """GetBuilder<%1${"$"}s>(
  assignId: true,
  builder: (%2${"$"}s) {
    return %3${"$"}s;
  },
)""", "SubjectController", "controller", widget
        )
    }

    private fun snippetGetX(widget: String): String {
        return String.format(
            """GetX<%1${"$"}s>(
  init: %1${"$"}s(),
  initState: (_) {},
  builder: (%2${"$"}s) {
    return %3${"$"}s;
  },
)""", "SubjectController", "controller", widget
        )
    }

    private fun blocBuilderSnippet(widget: String): String {
        return String.format(
            "BlocBuilder<%1\$s, %2\$s>(\n" +
                    "  builder: (context, state) {\n" +
                    "    return %3\$s;\n" +
                    "  },\n" +
                    ")", "SubjectBloc", "SubjectState", widget
        )
    }

    private fun blocListenerSnippet(widget: String): String {
        return String.format(
            ("BlocListener<%1\$s, %2\$s>(\n" +
                    "  listener: (context, state) {\n" +
                    "    // TODO: implement listener}\n" +
                    "  },\n" +
                    "  child: %3\$s,\n" +
                    ")"),
            "SubjectBloc",
            "SubjectState",
            widget
        )
    }

    private fun blocProviderSnippet(widget: String): String {
        return String.format(
            ("BlocProvider(\n" +
                    "  create: (context) => %1\$s(),\n" +
                    "  child: %2\$s,\n" +
                    ")"), "SubjectBloc", widget
        )
    }

    private fun blocConsumerSnippet(widget: String): String {
        return String.format(
            ("BlocConsumer<%1\$s, %2\$s>(\n" +
                    "  listener: (context, state) {\n" +
                    "    // TODO: implement listener\n" +
                    "  },\n" +
                    "  builder: (context, state) {\n" +
                    "    return %3\$s;\n" +
                    "  },\n" +
                    ")"),
            "SubjectBloc",
            "SubjectState",
            widget
        )
    }

    private fun repositoryProviderSnippet(widget: String): String {
        return String.format(
            ("RepositoryProvider(\n" +
                    "  create: (context) => %1\$s(),\n" +
                    "    child: %2\$s,\n" +
                    ")"), "SubjectRepository", widget
        )
    }
}