
import { useState, useRef, useEffect } from "react";
import { sendChatMessage } from "../../services/chatService";

export default function ChatAssistant() {
    const [question, setQuestion] = useState("");
    const [messages, setMessages] = useState([
        {
            role: "assistant",
            content: "Hello! I'm your RCET AI Assistant. How can I help you today?",
        },
    ]);
    const [loading, setLoading] = useState(false);

    const messagesEndRef = useRef(null);

    useEffect(() => {
        messagesEndRef.current?.scrollIntoView({
            behavior: "smooth",
        });
    }, [messages]);

    const handleSend = async (e) => {
        e.preventDefault();

        const userQuestion = question.trim();

        if (!userQuestion || loading) return;

        setQuestion("");

        setMessages((prev) => [
            ...prev,
            {
                role: "user",
                content: userQuestion,
            },
        ]);

        setLoading(true);

        try {
            const response = await sendChatMessage(userQuestion);

            setMessages((prev) => [
                ...prev,
                {
                    role: "assistant",
                    content: response.answer,
                    sources: response.sources || [],
                },
            ]);
        } catch (error) {
            setMessages((prev) => [
                ...prev,
                {
                    role: "assistant",
                    content: error.message,
                },
            ]);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="min-h-screen bg-slate-50 p-4 md:p-8">
            <div className="mx-auto flex h-[85vh] max-w-5xl flex-col overflow-hidden rounded-2xl border border-slate-200 bg-white shadow-sm">

                {/* Header */}
                <div className="border-b border-slate-200 bg-white p-5">
                    <h1 className="text-xl font-bold text-slate-800">
                        RCET AI Assistant
                    </h1>
                    <p className="mt-1 text-sm text-slate-500">
                        Ask questions about college documents and information.
                    </p>
                </div>

                {/* Messages */}
                <div className="flex-1 space-y-5 overflow-y-auto p-5">

                    {messages.map((message, index) => (
                        <div
                            key={index}
                            className={`flex ${
                                message.role === "user"
                                    ? "justify-end"
                                    : "justify-start"
                            }`}
                        >
                            <div
                                className={`max-w-[85%] rounded-2xl px-4 py-3 ${
                                    message.role === "user"
                                        ? "bg-cyan-600 text-white"
                                        : "bg-slate-100 text-slate-800"
                                }`}
                            >
                                <p className="whitespace-pre-wrap text-sm leading-6">
                                    {message.content}
                                </p>

                                {message.sources?.length > 0 && (
                                    <div className="mt-3 border-t border-slate-300 pt-2">
                                        <p className="mb-1 text-xs font-semibold">
                                            Sources
                                        </p>

                                        {message.sources.map((source, i) => (
                                            <p
                                                key={i}
                                                className="text-xs opacity-75"
                                            >
                                                {source.filename} — Chunk {source.chunk_index}
                                            </p>
                                        ))}
                                    </div>
                                )}
                            </div>
                        </div>
                    ))}

                    {loading && (
                        <div className="flex justify-start">
                            <div className="rounded-xl bg-slate-100 px-4 py-3 text-sm text-slate-500">
                                AI is thinking...
                            </div>
                        </div>
                    )}

                    <div ref={messagesEndRef} />
                </div>

                {/* Input */}
                <form
                    onSubmit={handleSend}
                    className="flex gap-3 border-t border-slate-200 p-4"
                >
                    <input
                        type="text"
                        value={question}
                        onChange={(e) => setQuestion(e.target.value)}
                        placeholder="Ask your college-related question..."
                        disabled={loading}
                        className="min-w-0 flex-1 rounded-xl border border-slate-300 px-4 py-3 text-sm outline-none focus:border-cyan-500 focus:ring-2 focus:ring-cyan-100"
                    />

                    <button
                        type="submit"
                        disabled={loading || !question.trim()}
                        className="rounded-xl bg-cyan-600 px-6 py-3 text-sm font-semibold text-white transition hover:bg-cyan-700 disabled:cursor-not-allowed disabled:opacity-50"
                    >
                        Send
                    </button>
                </form>

            </div>
        </div>
    );
}
