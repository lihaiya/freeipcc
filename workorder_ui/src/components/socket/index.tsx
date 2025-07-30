import io from 'socket.io-client';

const socket = io('http://localhost:30916/im/user');

export default socket;
