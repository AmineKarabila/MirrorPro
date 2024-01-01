import React, { useState, useEffect } from 'react';
import io from 'socket.io-client';

const socket = io('http://192.168.2.103:3001');

function extractVideoIdFromUrl(url) {
  // Regular Expression, die sowohl das "youtube.com" als auch das "youtu.be" Format unterstützt
  const regex = /(?:https?:\/\/)?(?:www\.)?(?:youtube\.com\/watch\?v=|youtu\.be\/)([a-zA-Z0-9_-]+)/;
  const match = url.match(regex);
  const videoId = match ? match[1] : null;
  console.log("Extrahierte Video-ID:", videoId);
  return videoId;
}


function Calendar() {
  const today = new Date();
  const dateString = today.toLocaleDateString('de-DE', { day: 'numeric', month: 'long', year: 'numeric' });
  return <div>{dateString}</div>;
}

function App() {
  const [videoId, setVideoId] = useState('');
  const [showPopup, setShowPopup] = useState(false);

  useEffect(() => {
    socket.on('videoId', (url) => {
      console.log("Empfangene YouTube-URL:", url);
      const id = extractVideoIdFromUrl(url);
      if (id) {
        setVideoId(id);
        setShowPopup(true);
      } else {
        console.error("Ungültige YouTube-URL empfangen");
      }
    });

    return () => {
      socket.off('videoId');
    };
  }, []);

  const closePopup = () => {
    setShowPopup(false);
    setVideoId('');
  };

  return (
    <div className="App">
      <div className="calendar">
        <Calendar />
      </div>
      {showPopup && videoId && (
        <div className="popup">
          <div className="popup-inner">
            <iframe
              width="560"
              height="315"
              src={`https://www.youtube.com/embed/${videoId}?autoplay=1&mute=1`}
              frameBorder="0"
              allow="autoplay; encrypted-media"
              allowFullScreen
              title="Video"
            ></iframe>
          </div>
        </div>
      )}
    </div>
  );
}

export default App;
