# cake-github

cake-github is a [cake](http://github.com/ninjudd/cake) plugin for providing an interface to the github API from your command-line.

Have you ever desired to create a new Github repository immediately after creating your project, without having to switch to your web browser and travel to github.com to do so? Desire no longer!

The plugin will provide an interface to all, or most of the API. You'll be able to create, delete, search, and modify repositories, all from your terminal with cake. And that isn't even the beginning.

## gotmilk

This plugin is basically the evolution of gotmilk. Gotmilk is deprecated (pretty sure nobody used it anyway) from this point foreward. Since there are already excellent command-line tools for github, and the JVM startup time is too significant for gotmilk to ever be practical, there is no use in it continuing.

This plugin is much more ambitious than gotmilk. Since cake already provides a persistent JVM, startup time is no longer a problem. Couple that with the fact that the target audience is Clojure users anyway, I think integration with cake as a cake plugin will be better in the long run.

Here is a brief, and incomplete list of improvements that cake-github has over gotmilk:

* Cake's persistent JVM means no JVM startup time.
* Enhanced (sane) formatting of API call results. It actually looks like a decent command-line tool now.
* Integrated into the worlds tastiest build tool.

# Usage

All commands are structured as two segments. The first being the 'category' of the API call, and the second being the actual command. Here is an example:

    rayne@ubuntu:~/cljprojs/cake-github$ cake users.info dom96

    Dominik Picheta - 
    -------------------------------------------
    http://github.com/dom96
    gravatar id:       d09fd89566b721c57bcb951acf2373f4
    following count:   24
    created at:        2010/04/18 07:05:30 -0700
    blog:              http://dom96.co.cc/
    public gist count: 66
    public repo count: 13
    company:           null
    login:             dom96
    location:          null

There are commands for doing all sorts of stuff that you typically do through the Github website.

# Command List

To find out more information about individual commands, do `cake help <command>`.

## Gists

    gists.new
    gists.meta
    gists.contents
    gists.show

## Users

    users.follow
    users.unfollow
    users.search
    users.followers
    users.following
    users.info
    users.watching
    users.set

## Repos

    repos.show
    repos.search
    repos.create
    repos.delete
    repos.fork
    repos.set
    repos.info
    repos.watch
    repos.unwatch
    repos.tags
    repos.pushable
    repos.network
    repos.languages
    repos.contributors
    repos.collaborators
    repos.add-collaborator
    repos.rm-collaborator
    repos.deploy 
    repos.add-deploy 
    repos.rm.deploy
    repos.visibility

## Issues

    issues.open
    issues.info
    issues.close
    issues.comment
    issues.tag
    issues.untag
    issues.tags
    issues.comments
    issues.show
    issues.reopen
    issues.search

# Installation

The whole thing is just a cake plugin. You'll want to be able to use it outside of a project, so you'll want to add the plugin to the :dev-dependencies in `~/.cake/project.clj`. You'll also need to add a :tasks key with the value `[cake-github.tasks]`. After that, do `cake deps --global`, and you should be all set.
