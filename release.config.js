const config = require('semantic-release-preconfigured-conventional-commits')

const releaseBranches = ["docs", "main"]
config.branches = releaseBranches
config.plugins.push(
    ["@semantic-release/exec", {
    }],
    ["@semantic-release/github", {
        "assets": [
            // Change this path if your pdf has another name.
            { "path": "docs/report.pdf" },
            // main branch txt files
            { "path": "main-repo/*.txt" },
            // ontology ttl file
            { "path": "main-repo/*.ttl" },
        ]
    }],
    ["@semantic-release/git", {
        "assets": ["CHANGELOG.md", "package.json"],
        "message": "chore(release)!: [skip ci] ${nextRelease.version} released"
    }],
)
module.exports = config