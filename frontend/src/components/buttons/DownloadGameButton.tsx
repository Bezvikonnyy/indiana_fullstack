import { FC } from "react";
import { DownloadIcon } from "../../assets/DownloadIcon";
import './DownloadGameButton.css';

interface DownloadGameButtonProps {
    fileUrl: string;
}

export const DownloadGameButton: FC<DownloadGameButtonProps> = ({ fileUrl }) => {
    const handleDownload = () => {
        const link = document.createElement("a");
        link.href = `http://localhost:8080${fileUrl}`;
        link.download = ""; // можно указать имя файла, если нужно
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    };

    return (
        <button onClick={handleDownload} className="download-game-button">
            <DownloadIcon />
        </button>
    );
};
