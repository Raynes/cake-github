# cake-github

cake-github is a [cake](http://github.com/ninjudd/cake) plugin for providing an interface to the github API from your command-line.

Have you ever desired to create a new Github repository immediately after creating your project, without having to switch to your web browser and travel to github.com to do so? Desire no longer!

The plugin will provide an interface to all, or most of the API. You'll be able to create, delete, search, and modify repositories, all from your terminal with cake. And that isn't even the beginning.

# gotmilk

This plugin is basically the evolution of gotmilk. Gotmilk is deprecated (pretty sure nobody used it anyway) from this point foreward. Since there are already excellent command-line tools for github, and the JVM startup time is too significant for gotmilk to ever be practical, there is no use in it continuing.

This plugin is much more ambitious than gotmilk. Since cake already provides a persistent JVM, startup time is no longer a problem. Couple that with the fact that the target audience is Clojure users anyway, I think integration with cake as a cake plugin will be better in the long run.

Here is a brief, and incomplete list of improvements that cake-github has over gotmilk:

* Cake's persistent JVM means no JVM startup time.
* Enhanced (sane) formatting of API call results. It actually looks like a decent command-line tool now.
* Integrated into the worlds tastiest build tool.
