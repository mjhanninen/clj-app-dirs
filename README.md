# app-dirs

Cross-platform application and user directories for Clojure.

## Status

Alpha, incomplete, totally untested.

## Depending

```clojure
mjhanninen/app-dirs {:git/url "https://github.com/mjhanninen/clj-app-dirs.git"
                     :sha "<grab-latest-git-sha>"}
```

## Usage

There is only one namespace to require:

```clojure
(require 'app-dirs)
```

The functions returning paths for "user" directories don't take arguments:

```clojure
(app-dirs/home-dir)
;; => #[object[File "/home/jsmith"]
```

The functions returning paths for "application" directories take the
organization and application name as arguments:

```clojure
(app-dirs/data-dir "ACME" "Frobnator")
;; => #[object[File "/home/jsmith/.local/share/frobnator"]
```

## Credits

Heavily inspired by

- Rust crate [directories][rs-directories]

[rs-directories]: https://crates.io/crates/directories

## License

Copyright © 2020 Matti Hänninen

Distributed under the Eclipse Public License either version 1.0 or (at your
option) any later version.
