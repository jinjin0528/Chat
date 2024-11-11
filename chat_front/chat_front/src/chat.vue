<template>
    <div>
      <h1>Chat Room</h1>
      <div v-for="message in messages" :key="message.id">
        {{ message.sender }}: {{ message.content }}
      </div>
      <input v-model="messageContent" placeholder="Enter your message" @keyup.enter="sendMessage"/>
    </div>
  </template>
  
  <script setup>
  import { ref, onMounted } from "vue";
  import SockJS from "sockjs-client";
  import Stomp from "stompjs";
  
  // 상태 변수 정의
  const stompClient = ref(null);
  const messages = ref([]);
  const messageContent = ref("");
  
  // 웹소켓 연결 함수
  function connect() {
    const socket = new SockJS("http://localhost:8080/ws");
    stompClient.value = Stomp.over(socket);
    stompClient.value.connect({}, frame => {
      console.log("Connected: " + frame);
      stompClient.value.subscribe("/topic/public", tick => {
        messages.value.push(JSON.parse(tick.body));
      });
    });
  }
  
  // 메시지 전송 함수
  function sendMessage() {
    if (messageContent.value && stompClient.value) {
      const chatMessage = {
        sender: "User",
        content: messageContent.value,
        type: "CHAT"
      };
      stompClient.value.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
      messageContent.value = ""; // 메시지 입력 초기화
    }
  }
  
  // 컴포넌트가 마운트될 때 연결 실행
  onMounted(connect);
  </script>
  
  <style scoped>
  /* 필요에 따라 스타일 추가 */
  </style>
  