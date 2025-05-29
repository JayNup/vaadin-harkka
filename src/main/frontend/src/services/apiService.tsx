import React, { useState } from 'react';
import axios from 'axios';

export const postData = async (url: string, data: any) => {
    const csrfToken = document.querySelector('meta[name="csrf-token"]')?.getAttribute('content');
    const headers = csrfToken ? { 'X-CSRF-TOKEN': csrfToken } : {};
    const response = await axios.post(url, data, { headers });
    return response.data;
};

const ExampleComponent: React.FC = () => {
    const [response, setResponse] = useState(null);

    const handlePostRequest = async () => {
        try {
            const data = await postData('/api/endpoint', { key: 'value' });
            setResponse(data);
        } catch (error) {
            console.error('Error:', error);
        }
    };

    return (
        <div>
            <button onClick={handlePostRequest}>Send POST Request</button>
    {response && <div>Response: {JSON.stringify(response)}</div>}
    </div>
    );
    };

    export default ExampleComponent;