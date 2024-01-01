import React, { useState, useEffect } from 'react';


function Calendar() {
  const today = new Date();
  const dateString = today.toLocaleDateString('de-DE', { day: 'numeric', month: 'long', year: 'numeric' });

  return (
    <div>{dateString}</div>
  );
}

function App() {
  const [showPopup, setShowPopup] = useState(false);

  useEffect(() => {
    setShowPopup(true);
  }, []);

  const closePopup = () => {
    setShowPopup(false);
  };

  return (
    <div className="App">
      <div className="calendar">
        <Calendar />
      </div>
      {showPopup && (
        <div className="popup">
          <div className="popup-inner">
            <button onClick={closePopup}>Schließen</button>
            <iframe
              width="560"
              height="315"
              src="https://www.youtube.com/embed/Robzc9p1l50?autoplay=1&mute=1"
              frameborder="0"
              allow="autoplay; encrypted-media"
              allowfullscreen
              title="Video"
            ></iframe>
          </div>
        </div>
      )}
    </div>
  );
}

export default App;
