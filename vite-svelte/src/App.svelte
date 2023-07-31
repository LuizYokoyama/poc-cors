<script lang="ts">

  import Keycloak, { KeycloakInitOptions } from "keycloak-js";


  // Keycloak
  let instance = {
    url: "http://127.0.0.1:8080",
    realm: "my",
    clientId: "svelte",
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

<main>

  <h1>Vite + Svelte</h1>

  <p>Palavras kasjfdlajsldfjlal</p>

  <button on:click={getWords}>GET Words</button>

  {#if words !== undefined}
    <p>Palavras: {words}</p>
  {/if}




</main>

<style>

</style>
