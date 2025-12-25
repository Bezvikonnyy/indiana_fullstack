export async function registerRequest(
    username: string,
    password: string,
    roleId: number,
    inviteCode: string
) {
    const payload = {
        username,
        password,
        roleId,
        inviteCode: inviteCode.trim(),
    };

    const response = await fetch("http://localhost:8080/api/user/registration", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
    });

    if (!response.ok) {
        let errorMessage = "Неизвестная ошибка";

        try {
            const contentType = response.headers.get("content-type");
            if (contentType && contentType.includes("application/json")) {
                const errorData = await response.json();
                errorMessage = errorData.message || JSON.stringify(errorData);
            } else {
                errorMessage = await response.text();
            }
        } catch {}

        throw new Error(errorMessage);
    }

    return true;
}
