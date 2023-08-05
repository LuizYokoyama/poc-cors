 <script lang="ts">

  import Keycloak from "keycloak-js";


  // Keycloak
  let instance = {
    url: 'http://localhost:8080/auth/',
    realm: 'my',
    clientId: 'svelte',
  };

  let kc = new Keycloak(instance);

/*
  keycloak.init({ onLoad: 'login-required' }).success(function(authenticated) {
    alert(authenticated ? 'authenticated' : 'not authenticated');
  }).error(function() {
    alert('failed to initialize');
  });
  */

  kc.init({ onLoad: 'login-required' }).then(function(authenticated) {
    alert(authenticated ? 'authenticated' : 'not authenticated');
  }).catch(function() {
    alert('failed to initialize');
  });

  /*
  function logout() {
    //
    kc.logout('http://auth-server/auth/realms/svelte/protocol/openid-connect/logout?redirect_uri=encodedRedirectUri')
    //alert("Logged Out");
  }
  */

/*
  let initOptions: KeycloakInitOptions = { onLoad: "login-required" };
  kc
          .init(initOptions)
          .then(function (authenticated) {
            console.info("Authenticated");
          })
          .catch(function () {
            alert("failed to initialize");
          });
*/

  //Call API
  let words;
  let endPointURL = "http://localhost:5001/read/get";
  async function getWords() {
    const response = await fetch(endPointURL, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${kc.token}`,
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

  <button >teste</button>

    <button on:click={getWords()}>
        Clicks: {words}
    </button>


</main>



 <style>
   :root {
     font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen,
     Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
   }

   main {
     text-align: center;
     padding: 1em;
     margin: 0 auto;
   }

   img {
     height: 3rem;
     width: 3rem;
   }

   h1 {
     color: #ff3e00;
     text-transform: uppercase;
     font-size: 1.8rem;
     font-weight: 80;
     line-height: 1.1;
     margin: 2rem auto;
     max-width: 14rem;
   }

   p {
     max-width: 14rem;
     margin: 1rem auto;
     line-height: 1.35;
   }

   @media (min-width: 480px) {
     h1 {
       max-width: none;
     }

     p {
       max-width: none;
     }
   }
   button {
     font-family: inherit;
     font-size: inherit;
     padding: 1em 2em;
     color: #ff3e00;
     background-color: rgba(255, 62, 0, 0.1);
     border-radius: 2em;
     border: 2px solid rgba(255, 62, 0, 0);
     outline: none;
     width: 200px;
     font-variant-numeric: tabular-nums;
     cursor: pointer;
   }

   button:focus {
     border: 2px solid #ff3e00;
   }

   button:active {
     background-color: rgba(255, 62, 0, 0.2);
   }
 </style>