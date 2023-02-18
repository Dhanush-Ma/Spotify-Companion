/*window.onSpotifyWebPlaybackSDKReady = () => {
  const token = 'BQCw80TKn3hJxDPBg_zgc3O96Nvij1nS-iP7R5Kw2aMGi21aRqiFSbAtbTURTtGqDakoj0EaTsQ509j-URT8tLJSHEynNJhWxVWW4GTTgVZL2H4n9pkDI0F_wLuxdUpSepe2Fr0az7tLAAQaYey7fwzpuKgaL7kwllG-kIJc4KCii3Nio27RQcyxVDRBDYDLsR8WlvbAifTYWN-FvKBO';
  const player = new Spotify.Player({
    name: 'Web Playback SDK Quick Start Player',
    getOAuthToken: cb => { cb(token); },
    volume: 0.5
 });
 
   // Ready
  player.addListener('ready', ({ device_id }) => {
    console.log('Ready with Device ID', device_id);
  });

  // Not Ready
  player.addListener('not_ready', ({ device_id }) => {
    console.log('Device ID has gone offline', device_id);
  });
  
   player.connect();
*/