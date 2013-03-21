same: a tool to find duplicate lines of code
============================================

> "Mechanical means can't prove good code, but they can often prove bad code.""

This is the beginning of a nifty tool to combat Copy & Paste attacks.

Some [example output](https://gist.github.com/2356c4677edb49eca331#L140)
showing places in [pry](http://github.com/pry/pry) that are duplicated. Note
that this is with the default settings. Here are the options:

Usage
-----

* -f filter       filter all files with this filter (none, trim, baan, java)
* -m minimalSize  minimal fragment length [default: 10]

The `trim` filter is especially useful, since much duplication gets
indented/outdented immediately after copy and pasting. The `same` script
actualy defaults this to on, but you can override it with `-f none` (though I
doubt you'll ever really want to).

There should be more filters (and rewriting this from Java would make better
filtration possible.) For now, you can rely on VCS and do like this (using
zsh's recursive globbing):

    sed -i 's/\s*end//' **/*.rb
    same -m 3 **/*.rb
    git checkout .

See how it uses a more aggressive `-m` limit. This can be very informative, but
it's usually nice to start with the default of 10 then zoom in from there. You
can also benefit from `-m 1`, but it takes good pre-filtering.

Prerequisite
------------

You have to have a Java Runtime Environment. For Gentoo, I emerged
`dev-java/icedtea-bin`, and for SuSE Linux I installed `java-1_6_0-openjdk`. OS
X comes with one by default as far as I know.

Installation
------------

Currently, it has this odd deal where the `same-classes.zip` file has to live
in the same dir as the `same` script. Here's a quick way to get it to work:

    cp bin/* ~/bin/

Or, you can just run it from the cloned dir, e.g.:

    cd ~/src
    git clone http://github.com/rking/same
    cd my-proj
    ~/src/same/bin/same **/*.c

So that means you could, in your `~/.bashrc` or `~/.zshrc`:

    alias same=~/src/same/bin/same

Note that I (☈king) am no Java pro, so if you have any problems, I'd love to
help walk you through them and make this README better. Or, of course, if *you*
are the Java pro, I'd like to hear about best practices for deploying a script
like this.

License
-------

This software is open source, under the GNU Public License. See GPL.txt.

Thanks
------

Huge thanks to Marnix Klooster, who wrote the original. This is his Thanks:

-----

Thanks to Baan for giving me the opportunity to write
this software, and to make it open source.

All feedback is welcome at marnix@users.sourceforge.net.

Groetjes,
 <><
Marnix
--
Marnix Klooster
marnix@users.sourceforge.net
