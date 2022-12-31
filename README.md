<h1 align="center">
  <a href="https://github.com/SamTheCoder777/Custom-Spotify-Manager">
    Custom Spotify Manager
  </a>
</h1>

<div align="center">
  Allows user to freely manage their spotify. The user can create playlists filled with a certain genre, or get random songs from their multiple playlists and many more.!
  <br />
  <br />
  <a href="https://github.com/SamTheCoder777/Custom-Spotify-Manager/issues/new?assignees=&labels=bug&title=bug%3A+">Report a Bug</a>
  ·
  <a href="https://github.com/SamTheCoder777/Custom-Spotify-Manager/issues/new?assignees=&labels=enhancement&title=feat%3A+">Request a Feature</a>
  .
  <a href="https://github.com/SamTheCoder777/Custom-Spotify-Manager/discussions">Ask a Question</a>
</div>

<div align="center">
<br />

[![license](https://img.shields.io/github/license/dec0dOS/amazing-github-template.svg?style=flat-square)](LICENSE)
[![GitHub release](https://img.shields.io/github/release/SamTheCoder777/Custom-Spotify-Manager?include_prereleases=&sort=semver&color=blue)](https://github.com/SamTheCoder777/Custom-Spotify-Manager/releases/)
[![OS - Windows](https://img.shields.io/badge/OS-Windows-blue?logo=windows&logoColor=white)](https://www.microsoft.com/ "Go to Microsoft homepage")
[![Created by - SamTheCoder777](https://img.shields.io/badge/Created_by-SamTheCoder777-blue)](https://github.com/SamTheCoder777)

</div>

<details open="open">
<summary>Table of Contents</summary>

- [About](#about)
  - [Built With](#built-with)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Usage](#usage)
    - [Running Custom Spotify Manager](#running-custom-spotify-manager)
    - [Set up the program](#set-up-the-program)
    - [List of commands](#list-of-commands)
- [Contributing](#contributing)
- [Support](#support)
- [License](#license)
- [Acknowledgements](#acknowledgements)

</details>

---

## About

<table>
<tr>
<td>

I am a music listener just like everyone and I listen to them whenever I am working, reading, studying or gaming. I created this program because I wanted to have more control over Spotify and personalize it to the fullest. I have always wanted to enjoy playlists that actually have diverse songs from different artists and Spotify's own auto personalized playlists always failed to achieve that. They were always so unpolished out, they recommend songs that were obviously out of place and sometimes it was so random I did not know how they came up with it. Because of all these reasons, I decided to create a program that will achieve a goal of recommending me songs of a specific genre anytime I wanted. In addition to this, I always wanted a feature that will allow me to mix and match songs from multiple playlists and randomize them so I do not have to manually add songs from different playlists to one playlist. I have been coding since few years ago and this is my first ever serious project that I have decided to work on and finish it. Because of this, I am very much proud of this project and hopefully you will enjoy it as well. I will keep updating this project whenever I have time or have a need for a new feature.

Key features of **Custom Spotify Manager**:

- Search artists or genres by their name
- Search top tracks for a given artist
- Search for all existing genres
- Find out who are my top artists
- Find out what are my top songs
- Create a playlist filled with random songs from set category playlists
- Create a playlist with random songs from a list of playlists
- Create a playlist filled with artists erlated with given genres
- Create a playlist filled with recommended songs of a genre
- Look cool
- More features planned to be added...

</td>
</tr>
</table>

### Built With

- [Spotify API](https://developer.spotify.com/documentation/web-api/)
- [Spotify Web API Java](https://github.com/spotify-web-api-java/spotify-web-api-java)

## Getting Started

### Prerequisites

Make sure you have the Java 17 or higher installed.

You can easily run this program in Windows command list.
> This program is not tested on other operating systems.

You can verify your java version in CMD with:

```sh
java -version
```

Then download the most recent release.

### Usage

#### Running-Custom-Spotify-Manager

After downloading the program, in the program directory:

```sh
java -jar customSpotifyManager.jar
```

Or in windows, you can run the provided batch file named: runCustomSpotifyManager.bat


#### Set-up-the-program

Please follow these steps:
1. Go to spotify developer dashboard ([here](https://developer.spotify.com/dashboard/login)) and log in

2. On the dashboard, click create an app and follow instructions
![Preview](docs/images/create-an-app.png)

3. In the top right corner, click edit settings
![Preview](docs/images/edit-settings)

4. In the "Redirect URIs" section, write and click add:
> http://localhost:8080

![Preview](docs/images/add-uri.png)

5. Scroll down and save

6. At top right, the clientID and token (click "show client secret") are located

7. Open setup.txt and paste your clientID and token (client secret) into the provided space


![Preview](docs/images/clientid-token-setup.png)

8. Restart program if it was open

9. Run command:
```sh
init
```

10. Copy and paste the returned url into your browser.

11. Click agree and paste your code into setup.txt:
![Preview](docs/images/get-code.png)



12. At this step, your setup.txt should looke like:


![Preview](docs/images/setup-txt.png)



13. Press enter in the program and you are all set!

#### List-of-commands

The commands are not case sensitive


| Name                       | command            | Description                                                                 |
| -------------------------- | ------------------------------ | --------------------------------------------------------------------------- |
| Initialize                 | init                           | Set up program for the first run                                            |
| Create Category Playlist   | CreateCategoryPlaylist         | Creates playlist filled with the songs from category playlist               |
| Create Custom Playlist     | CreateCustomPlaylist     | Creates playlist filled with random songs from the given playlistsIDs provided in playlistList.txt |
| Create Explore Artists Playlist| Create Explore Artists Playlist     | Create playlists filled with artists related with given genre                       |
| Create Playlist        | CreatePlaylist       | Creates playlist with given name                                                   |
| Create Recommended Playlist              | CreateRecommendedPlaylist                  | Creates playlist filled with recommended songs for a given genre     |
| Get Artist Top Tracks  | GetArtistTopTracks                  | Prints out top tracks for a given artist                                      |
| Get Category List               | GetCategoryList          | Prints out list of categories available          |
| Get Genre List             | GetGenreList                  | Prints out whole list for all available genres                                       |
| Get My Top Artists                | GetMyTopArtists            | Prints out the user's most played artists                                              |
| Get My Top Tracks        | GetMyTopTracks                  | Prints out the user's most played tracks                                                 |
| Get Recommendations | GetRecommendations                  | Prints out recommended tracks for a given genre                                          |
| Search Artists            | SearchArtists                  | Searches for the artist with given name                                      |
| Search Genre          |    SearchGenre               | Prints out genre that starts with given character                              |

## Contributing

First off, thanks for taking the time to contribute! Contributions are what makes the open-source community such an amazing place to learn, inspire, and create. Any contributions you make will benefit everybody else and are **greatly appreciated**.

Please try to create bug reports that are:

- _Reproducible._ Include steps to reproduce the problem.
- _Specific._ Include as much detail as possible: which version, what environment, etc.
- _Unique._ Do not duplicate existing opened issues.
- _Scoped to a Single Bug._ One bug per report.

## Support

Reach out to the maintainer at one of the following places:

- [GitHub discussions](https://github.com/SamTheCoder777/Custom-Spotify-Manager/discussions)

## License

This project is licensed under the **MIT license**.

See [LICENSE](LICENSE) for more information.

## Acknowledgements

Thanks for these awesome resources that were used during the development of the **Custom Spotify Manager**:

- <https://github.com/dec0dOS/amazing-github-template>
- <https://github.com/spotify-web-api-java/spotify-web-api-java>
- <https://github.com/MichaelCurrin/badge-generator>
- <https://developer.spotify.com/documentation/web-api/>
- <https://developer.spotify.com/console/>

