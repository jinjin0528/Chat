// 서버 URL 설정 (백엔드가 실행 중인 URL)
const BASE_URL = 'http://localhost:8080/api/chat';

// DOM 요소 선택
const searchInput = document.getElementById('searchInput');
const searchButton = document.getElementById('searchButton');
const chatRoomList = document.getElementById('chatRoomList');

// 채팅방 목록 가져오기
async function fetchChatRooms() {
    try {
        const response = await fetch(`${BASE_URL}/rooms`);
        const chatRooms = await response.json();
        displayChatRooms(chatRooms);
    } catch (error) {
        console.error('Error fetching chat rooms:', error);
    }
}

// 검색 기능
async function searchChatRooms(keyword) {
    try {
        const response = await fetch(`${BASE_URL}/rooms/search?keyword=${encodeURIComponent(keyword)}`);
        const chatRooms = await response.json();
        displayChatRooms(chatRooms);
    } catch (error) {
        console.error('Error searching chat rooms:', error);
    }
}

// 채팅방 목록 표시
function displayChatRooms(chatRooms) {
    chatRoomList.innerHTML = '';  // 기존 목록 지우기
    if (chatRooms.length === 0) {
        chatRoomList.innerHTML = '<li>No chat rooms found.</li>';
    } else {
        chatRooms.forEach(room => {
            const li = document.createElement('li');
            li.innerHTML = `
                <h2>${room.keyword || 'No Title'}</h2>
                <p>Creator ID: ${room.creatorId}</p>
            `;
            chatRoomList.appendChild(li);
        });
    }
}

// 초기화 - 전체 채팅방 목록 불러오기
fetchChatRooms();

// 검색 버튼 클릭 이벤트
searchButton.addEventListener('click', () => {
    const keyword = searchInput.value.trim();
    if (keyword) {
        searchChatRooms(keyword);
    } else {
        fetchChatRooms();  // 키워드가 없을 경우 전체 목록 로드
    }
});
