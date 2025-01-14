# CL dot IP

A tiny cljc library to get and set clipboard content.

## Quickstart

``` clojure
(require 'cl.ip)

(cl.ip/set "a string or value that will be str'd")

(cl.ip/get) ;=> "whatever's in the clipboard as a string"
```

## Status

Work in progress, expect broken things. Will stabilize at some point.

PRs for other platforms very welcome.

Implemented platforms

- [x] JVM
- [x] Linux with xclip
- [ ] Linux other
- [ ] OSX
- [ ] Windows
- [ ] Windows cygwin
- [ ] Node

## Goals

### Easy clojure API

The "simple" way of interacting with the clipboard from clojure jvm is
to use `java.awt.*` classes. This is a bit of a pain though, I just
want to get strings to and from the clipboard.

### Cross platform

Interacting with the clipboard is useful for
scripts that are meant to be run by a user. Example: the `pass` CLI
password manager.

Babashka doesn't include the awt classes for interacting with the
clipbaord (TODO find the clojurians slack log link with the
justification for this).

Clojure dialects that run on top of node don't have access to a built-in way of
doing this either.

### Succinct

I often want to grab some REPL output for copy-pasting to slack or
some website. This should have little friction, which is why the
namespace is called simply `cl.ip`, so if it has been required at some
point in the REPL session `(cl.ip/set some-val)` is all you need to
type.

## Non-goals

### Browser support

Browsers are sufficiently different that you should use another solution.

### Async API

The API will be only synchronous.


## Random links / prior art

* https://github.com/sindresorhus/clipboardy
* https://stackoverflow.com/questions/6710350/copying-text-to-the-clipboard-using-java
