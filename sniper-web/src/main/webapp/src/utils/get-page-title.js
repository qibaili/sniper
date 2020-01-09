import defaultSettings from '@/settings'

const title = defaultSettings.title || 'Sniper'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
