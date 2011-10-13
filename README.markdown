# cake-github

cake-github is a [cake](http://github.com/ninjudd/cake) plugin for providing an interface to the github API from your command-line.

Have you ever wanted to create a new Github repository immediately after creating your project without having to switch to your web browser and navigate to github.com to do so? This might be useful for you.

The plugin will provide an interface to all, or most of the API. You'll be able to create, delete, search, and modify repositories, all from your terminal with cake.

So, what does this have over the official Github command-line tool? Well, it's integrated with cake and written in Clojure. Mostly the integrated with cake part that matters. If you're already using cake, you've got everything you need. Just add this plugin to your global project and off you go.

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
