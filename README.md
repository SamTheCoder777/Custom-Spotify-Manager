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
[![GitHub tag](https://img.shields.io/github/tag/SamTheCoder777/Custom-Spotify-Manager?include_prereleases=&sort=semver&color=blue)](https://github.com/SamTheCoder777/Custom-Spotify-Manager/releases/)
[![CodeFactor](https://www.codefactor.io/repository/github/samthecoder777/custom-spotify-manager/badge)](https://www.codefactor.io/repository/github/samthecoder777/custom-spotify-manager)


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
    - [Set Up the Program](#set-up-the-program)
    - [List of Commands](#list-of-commands)
- [Extra Tutorials](#extra-tutorials)
    - [Create Custom Playlists](#create-custom-playlists)
- [Disclaimer](#disclaimer)
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
- Find out who are your top artists
- Find out what are your top songs
- Create "My top songs" playlist anytime instead of getting one by Spotify at the end of the year
- Get a playlist filled with your favorite artists
- Create a playlist filled with random songs from set category playlists
- Create a playlist with random songs from a list of playlists
- Create a playlist filled with artists erlated with given genres
- Create a playlist filled with recommended songs of a genre
- Look cool
</td>
</tr>
</table>

### Built With

- [Spotify API](https://developer.spotify.com/documentation/web-api/)
- [Spotify Web API Java](https://github.com/spotify-web-api-java/spotify-web-api-java)

## Getting Started

### Downloading Windows Terminal (Optional)

You can download a better version of **Windows Command Prompt** called **Windows Terminal**
([download link](https://www.microsoft.com/store/apps/9n0dx20hk701))

You will be able to click links and characters will not be broken

### Prerequisites

Make sure you have the Java 17.0.5 or higher installed.

You can easily run this program in Windows command list.
> This program is not tested on other operating systems.

You can verify your java version in CMD with:

```sh
java -version
```


### Usage

#### Running Custom Spotify Manager

After downloading the program, in the program directory:

```sh
java -jar CustomSpotifyManager.jar
```

Or in windows, you can run the provided batch file named: runCustomSpotifyManager.bat


#### Set Up the Program

Please follow these steps:
1. Go to spotify developer dashboard ([here](https://developer.spotify.com/dashboard/login)) and log in

2. On the dashboard, click create an app and follow instructions
<img src="docs/images/create-an-app.png" width="400"/>

3. In the top right corner, click edit settings
<img src="docs/images/edit-settings.png" width="400"/>


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
10. Click agree on the opened browser and your program should be set
>If the browser does not open automatically, move on to the next step
<br>

</br>

**If nothing happens:**

11. Copy and paste the returned url into your browser.

12. Click agree and paste your code into setup.txt:
<img src="docs/images/get-code.png" width="700"/>



12. At this step, your setup.txt should looke like:


![Preview](docs/images/setup-txt.png)



13. Press enter in the program and you are all set!

#### List of Commands

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
| Get My Top Artists                | GetMyTopArtists            | Prints out the user's most played artists and you can add songs by these artists to a playlist                                             |
| Get My Top Tracks        | GetMyTopTracks                  | Prints out the user's most played tracks and you can add these songs to a playlist                                              |
| Get Recommendations | GetRecommendations                  | Prints out recommended tracks for a given genre                                          |
| Search Artists            | SearchArtists                  | Searches for the artist with given name                                      |
| Search Genre          |    SearchGenre               | Prints out genre that starts with given character                              |

## Extra Tutorials
### Create Custom Playlists
1. Go to the page of one of playlists you want to get random songs from
<img src="docs/images/playlist.png" width="400"/>

2. Click on the "three dots"

3. Click on "Copy link to playlist"
<img src="docs/images/copy-playlist-link.png" width="400"/>

> https://open.spotify.com/playlist/37i9dQZF1DXcF6B6QPhFDv?si=acdd9277122146e5

4. Get the ID from the link
<img src="docs/images/playlistID.png" width="400"/>

> 37i9dQZF1DXcF6B6QPhFDv

5. Add it to the **playlistList.txt** and make sure to **SAVE** the file
<img src="docs/images/playlistList.png" width="400"/>

6. You can add multiple IDs by separating each IDs with new lines
<img src="docs/images/example-playlistID.png" width="400"/>

> Do not delete the hyphens in the file or the program may not work as expected

## Disclaimer

Command **"CreateCategoryPlaylist"** may not work consistently due to Spotify not supporting some category playlists in some countries or offers very limited amount of category playlists. If it does not work as expected, please use **"CreateCustomPlaylist"** instead by adding the ID of the playlists you wanted to get random songs from in **playlistList.txt**

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

