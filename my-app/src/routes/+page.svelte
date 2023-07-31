
<script lang="ts">
    import Keycloak, { KeycloakInitOptions } from "keycloak-js";

    // Keycloak
    let instance = {
        url: "http://localhost:8080",
        realm: "my",
        clientId: "myclient",
        clientSecret: "JAt2LkGmYZcKKKTpQYfbOERhN7xnYpDw",
        onLoad: 'login-required'
    };

    let keycloak = Keycloak(instance);
    let initOptions: KeycloakInitOptions = { onLoad: "login-required" };
    keycloak
        .init(initOptions)
        .then(function (authenticated) {
            console.info("Authenticated");
        })
        .catch(function () {
            alert("failed to initialize");
        });

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


</script>

<h1>Welcome to SvelteKit</h1>
<p>Visit <a href="https://kit.svelte.dev">kit.svelte.dev</a> to read the documentation</p>


<button on:click={getWords}>GET Words</button>

{#if words !== undefined}
    <p>Palavras: {words}</p>
{/if}