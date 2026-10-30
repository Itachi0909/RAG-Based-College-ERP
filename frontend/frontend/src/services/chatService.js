import axios from "axios";

const CHAT_API = axios.create({
    baseURL: "http://localhost:8001",
    headers: {
        "Content-Type": "application/json",
    },
});

export const sendChatMessage = async (question) => {
    try {
        const response = await CHAT_API.post("/chat", {
            question: question,
        });

        return response.data;
    } catch (error) {
        console.error("Chat API Error:", error);

        throw new Error(
            error.response?.data?.detail ||
            "Unable to connect to the AI assistant."
        );
    }
};