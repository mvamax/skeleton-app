export const fetchCommunes = (input) => {
  return fetch(`./communes?query=${input}`)
 .then(response => response.json())
 .then((json) => {
   return json.map((item) => {
     return { value: item.id, label: `${item.codeInsee} - ${item.libelle}` }
   })
 })
}
