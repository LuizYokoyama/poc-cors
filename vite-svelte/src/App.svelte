<script lang="js">

  import Keycloak from "keycloak-js";


  // Keycloak
  let instance = {
    url: 'http://localhost:8080/auth/',
    realm: 'my',
    clientId: 'svelte',
  };

  let kc = new Keycloak(instance);



  kc.init({ onLoad: 'login-required' }).then(function(authenticated) {
    alert(authenticated ? 'authenticated' : 'not authenticated');
  }).catch(function() {
    alert('failed to initialize');
  });



  //Call API
  let words;
  let endPointURL = "http://localhost:5001/read/get";
  async function getWords() {
    const response = await fetch(endPointURL, {
      method: "GET",
      headers: {
          'accept': '*/*',
          'Content-Type': 'application/json',
          'Authorization' : `Bearer ${kc.token}`,
      },
    });
    if (!response.ok) {
      console.log(response);
    }
    let w = await response.text();
    return w;

  }

  const wordsList = async () => {
      //update token
      kc?.updateToken(50).then(async function () {
          let wordsResp = await getWords();
          //alert(wordsResp);
          words = wordsResp + ' ';
      });
  };

 async function clear(){
      words = '';
  }
  // const csrfToken = document.getElementsByTagName('meta')["_csrf"].content;
  // const csrfHeader = document.getElementsByTagName('meta')["_csrf_header"].content;


  var csrfParameter = window.$("meta[name='_csrf_parameter']").attr("content");
  var csrfHeader = window.$("meta[name='_csrf_header']").attr("content");
  var csrfToken = window.$("meta[name='_csrf']").attr("content");


  // // using JQuery to send a non-x-www-form-urlencoded request
  // var headers = {};
  // headers[csrfHeader] = csrfToken;
  // $.ajax({
  //     url: "http://loca[:5001/write/post",
  //     type: "POST",
  //     headers: headers,
  //     ...
  // });
  //

  function getCookie(name) {
      if (!document.cookie) {
          return null;
      }
      const xsrfCookies = document.cookie.split(';')
          .map(c => c.trim())
          .filter(c => c.startsWith(name + '='));

      if (xsrfCookies.length === 0) {
          return null;
      }
      return decodeURIComponent(xsrfCookies[0].split('=')[1]);
  }

</script>

<main>

  <h1>Vite + Svelte</h1>

  <p>Palavras cadastradas</p>

  <button on:click={clear}>
      limpa
  </button>

    <button on:click={wordsList}>
        Click to GET
    </button>

    {#if words !== undefined}
        <p>{words}</p>
    {/if}

    <br>
    <label>
        Adicionar palavras:
        <input
                type="text"
                autocomplete="off"
                on:keydown={async (e) => {
				if (e.key === 'Enter') {
					const input = e.currentTarget;
					const word = input.value;

 //update token
                    kc?.updateToken(50).then(async function () {
                        const csrfToken = getCookie('XSRF-TOKEN');

                        const response = await fetch('http://localhost:5001/write/post', {
                            method: 'POST',
                            body: word,
                            credentials: "include",
                            headers: {
                                'accept': '*/*',
                                'Content-Type': 'application/json',
                                'Authorization' : `Bearer ${kc.token}`,
                                'X-XSRF-TOKEN': csrfToken,
                            }
                        });

                        let resp = await response.text();

                       // alert(resp);
                        if (words !== undefined){
                            words += resp + ' ';
                        } else {
                            words = resp + ' ';
                        }
                        input.value = '';
                    });
				}
			}}

        />
    </label>


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