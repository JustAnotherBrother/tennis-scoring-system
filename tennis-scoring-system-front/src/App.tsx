import React, { useEffect, useState } from 'react';

import './App.css'

const API_URL_INITIALISATION = "http://localhost:8080/api/v1/games/initialise";
const API_URL_UPDATE_POINTS = "http://localhost:8080/api/v1/games/update/player/points?playerName=";


export interface PlayerProps {
  name: string;
  points: number;
  winner: false;
}

export interface GameProps {
  playerA: PlayerProps;
  playerB: PlayerProps;
  deuceMode: boolean;
  gameOver: boolean;
}

function App() {
  const [game, setGame] = useState<GameProps>();
  const [callUrl, setCallUrl] = useState("");

  const [deuceModeStyle, setDeuceModeStyle] = useState({ color: 'black' });


  const fetchData = async (URL) => {
    const result = await fetch(URL);
    result.json().then(json => {
      setGame(
        {
          "playerA": json.playerA,
          "playerB": json.playerB,
          "gameOver": json.gameOver,
          "deuceMode": json.deuceMode
        }
      )

      console.log(json);
    })
  };

  useEffect(() => {
    fetchData(API_URL_INITIALISATION);
  }, []);

  useEffect(() => {
    if (callUrl && callUrl !== "") {
      fetchData(callUrl);
      setCallUrl("");
    }
  }, [callUrl]);

  useEffect(() => {
    if (game?.deuceMode) {
      setDeuceModeStyle(game?.deuceMode ? { color: 'red' } : { color: 'black' });
    }
  }, [game?.deuceMode]);

  const resetGame = () => {
    setDeuceModeStyle({ color: 'black' });
    setCallUrl(API_URL_INITIALISATION);
  };

  const updatePlayerPoints = (playerName) => {
    setCallUrl(API_URL_UPDATE_POINTS + playerName);
  }

  function writePlayerComponent(player) {
    return <tr>
      <td><button
        onClick={() => updatePlayerPoints(player.name)}
        disabled={game?.gameOver}>
        give point</button></td>
      <td>{player.name}</td>
      <td style={deuceModeStyle}>{player.points}</td>
      <td>{player.winner ? "Champion" : ""}</td>
    </tr>;
  }

  if (!game) {
    return <>Game has not yet started</>
  }
  return <div>
    <table>
      <tr>
        <th><button
          onClick={() => resetGame()}>
          Reset game</button></th>
        <th>Player</th>
        <th style={deuceModeStyle}>Points</th>
        <th>Winner</th>
      </tr>
      {writePlayerComponent(game.playerA)}
      {writePlayerComponent(game.playerB)}
      {game?.deuceMode &&
        <tr style={deuceModeStyle}>
          Deuce mode
        </tr>

      }
    </table>

  </div>;


}


export default App;