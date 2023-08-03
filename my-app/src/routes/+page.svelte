
<script lang="js">

    import Keycloak from 'keycloak-js';

    let words;


    // Keycloak
    const keycloak = new Keycloak({
        url: 'http://localhost:8080',
        realm: 'my',
        clientId: 'svelte'
    });






    async function fetchWords() {

        const response = await fetch('http://localhost:5001/read/get', {
            headers: {
                accept: 'application/json',
                authorization: `Bearer ${keycloak.token}`
            }
        });

        return response.json();
    }



    async function get(){
        try {
            const authenticated = await keycloak.init({
                onLoad: 'login-required'
            });
            console.log(`User is ${authenticated ? 'authenticated' : 'not authenticated'}`);
        } catch (error) {
            console.error('Failed to initialize adapter:', error);
        }
/*
        try {
            await keycloak.updateToken(30);
        } catch (error) {
            console.error('Failed to refresh token:', error);
        }
*/
        words =  await fetchWords();
    }
/*
    //Call API
    let words;
    let endPointURL = "http://localhost:5001/read/get";
    async function getWords() {
        const response = await fetch(endPointURL, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${keycloak.token}`,
            },
        });
        if (!response.ok) {
            console.log(response);
        }
        words = await response.json();

    }
*/

</script>

<h1>Welcome to SvelteKit</h1>
<p>Visit <a href="https://kit.svelte.dev">kit.svelte.dev</a> to read the documentation</p>


<button on:click={get()}>GET Words</button>

{#if words !== undefined}
    <p>Palavras: {words}</p>
{/if}